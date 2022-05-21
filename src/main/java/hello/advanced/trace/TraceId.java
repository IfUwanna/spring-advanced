package hello.advanced.trace;

import java.util.UUID;

public class TraceId {
    private String id;
    private int level;

    public TraceId() {
          this.id = createId();
          this.level = 0;
    }

    private String createId() {
        return UUID.randomUUID().toString().substring(0,8);
    }

    public TraceId createNextId() {
        return new TraceId(id, level + 1); // 동일 id에 level만 하나 증가!
    }

    public TraceId createPreviousId() {
        return new TraceId(id, level - 1); // 동일 id에 level만 하나 감소!
    }

    public boolean isFirestLevel(){
        return level==0;
    }

    public TraceId(String id, int level) {
        this.id = id;
        this.level = level;
    }


    public String getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }

}
