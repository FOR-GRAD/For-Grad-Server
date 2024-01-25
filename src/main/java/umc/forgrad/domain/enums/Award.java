package umc.forgrad.domain.enums;

//Enum을 사용하면 간편하게 다양한 시상 내역을 나타낼 수 있으며,
// 별도의 테이블을 사용하면 데이터베이스의 일관성을 유지할 수 있습니다.
// JSON으로 저장하는 방식은 유연성이 있지만, 일반적으로는 추천되지 않습니다.  ->enum으로 하자.
public enum Award {
    GRAND_PRIZE,
    EXCELLENT_PRIZE,
    GOOD_PRIZE,
    ENCOURAGEMENT_PRIZE
}
