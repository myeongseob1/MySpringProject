package hello.core.member;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component //memoryMemberRepository
public class MemoryMemberRepository implements MemberRepository {
    //실무에서 사용시 동시성 이슈가 있을수 있어 ConcurrentHashMap을 사용한다
    private static Map<Long, Member> store = new HashMap<>();

    @Override
    public void save(Member member) {
        store.put(member.getId(),member);
    }

    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }
}
