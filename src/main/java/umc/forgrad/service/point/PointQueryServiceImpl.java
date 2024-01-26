package umc.forgrad.service.point;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import umc.forgrad.converter.PointConverter;
import umc.forgrad.dto.point.PointResponseDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static umc.forgrad.service.common.ConnectionResponse.getResponse;

@Service
@Slf4j
public class PointQueryServiceImpl implements PointQueryService {

    @Override
    public PointResponseDto.MyPointResponseDto getMyPointList(int page, HttpSession session) throws IOException {

        String hsportalUrl = "https://hsportal.hansung.ac.kr/ko/mypage/point/" + page;

        Connection.Response pointResponse = getResponse(session, hsportalUrl);

        Document pointDocument = pointResponse.parse();

        // 페이지 포인트 내역 조회
        Elements rows = pointDocument.select("ul.black.inner > li.tbody");

        List<PointResponseDto.PointDto> myPointList = new ArrayList<>();

        for (Element row : rows) {
            String title = row.select("span.title").text();
            String rewardPoints = row.select("span.change").text();
            String accumulatedPoints = row.select("span.accumulation").text();
            String date = row.select("span.date").text();

            PointResponseDto.PointDto myPointListDto = PointResponseDto.PointDto.builder()
                    .title(title)
                    .rewardPoints(rewardPoints)
                    .accumulatedPoints(accumulatedPoints)
                    .date(date)
                    .build();

            myPointList.add(myPointListDto);
        }

        // 마지막 페이지 크기
        int pageSize = Integer.parseInt(Objects.requireNonNull(pointDocument.select("div[data-role=pagination]").first()).attr("data-total"));

        // 페이지 요소 개수
        int listSize = myPointList.size();

        // 포인트 요약 가져오기
        String semesterPoints = pointDocument.select("#ModuleMileagePointForm > div.mileage > ul > li:nth-child(1) > p > span").text();
        String carryoverPoints = pointDocument.select("#ModuleMileagePointForm > div.mileage > ul > li:nth-child(2) > p > span").text();
        String accumulatedPoints = pointDocument.select("#ModuleMileagePointForm > div.mileage > ul > li:nth-child(5) > p > span").text();
        String graduationPoints = pointDocument.select("#ModuleMileagePointForm > div.mileage > ul > li:nth-child(6) > p > span").text();

        PointResponseDto.PointSummaryDto pointSummaryDto = PointResponseDto.PointSummaryDto.builder()
                .semesterPoints(semesterPoints)
                .carryoverPoints(carryoverPoints)
                .accumulatedPoints(accumulatedPoints)
                .graduationPoints(graduationPoints)
                .build();

        return PointConverter.toMyPointResponseDto(listSize, pageSize, pointSummaryDto, myPointList);

    }

}
