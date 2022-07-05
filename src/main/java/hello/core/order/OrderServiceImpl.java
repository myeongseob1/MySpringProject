package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/*
    의존관계 주입은 스프링 컨테이너가 관리하는 스프링 빈이어야 작동한다.
    스프링 빈이 아닌 일반 자바 클래스에서는 @Autowired를 써도 아무 의미가 없다.
    ★★ 생성자 주입을 선택해라 -> 수정자 주입은 가끔쓰고 필드주입은 절때 쓰지말아라 ★★
*/

@Component
//@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    //필드 주입 -> 쓰면 안된다
    //@Autowired 코드가 간결하지만 외부에서 변경할수가 없어 테스트하기가 어렵다
    //테스트 코드 정도에서만 쓴다
    // final을 사용하여 변경할수 없게 설정
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    //private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    //private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    //=> 정책 변경시 코드가 변경되는데 OCP를 위반한 코드 변경이다


    //setter 주입방식(수정자 주입)
    //선택, 변경 가능성이 있는 의존관계에 사용
/*
    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        System.out.println("memberRepository = " + memberRepository);
        this.memberRepository = memberRepository;
    }
    @Autowired
    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        System.out.println("discountPolicy = " + discountPolicy);
        this.discountPolicy = discountPolicy;
    }
*/
    // 생성자 주입 방식 : 불변, 필수 의존관계에 사용
    // 생성자가 딱 하나만 있을경우 @Autowired가 생략되어있다고 생각하면됨(생성자 주입 자동)
    // 객체 생성하여 bean 등록할때 생성자를 자동으로 호출해서 같이 의존성이 주입됨
    // 요즘은 주로 생성자 주입방식을 많이 사용
    // 불변, 누락방지, final 키워드를 넣을수 있음

    //@RequiredArgsConstructor 가 생성자를 자동으로 생성 해준다.
    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }



    // 일반 메서드 주입도 가능하나 일반적으로는 잘 사용하지 않는다

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member,itemPrice);

        return new Order(memberId,itemName,itemPrice,discountPrice);
    }
    //테스트 용도
    public MemberRepository getMemberRepository(){
        return memberRepository;
    }
}
