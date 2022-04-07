package hello.core.discount;

import hello.core.member.Member;

public interface DiscountPolicy {
    /**
     * 할인금액을 반환
     * @param member
     * @param price
     * @return 할인대상 금액
     */
    int discount(Member member, int price);
}
