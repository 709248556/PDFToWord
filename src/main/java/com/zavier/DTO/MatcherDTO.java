package com.zavier.DTO;

public class MatcherDTO {
    Integer start;
    Integer end;

    public MatcherDTO(Integer start,Integer end) {
        this.start = start;
        this.end = end;
    }
    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }
}
