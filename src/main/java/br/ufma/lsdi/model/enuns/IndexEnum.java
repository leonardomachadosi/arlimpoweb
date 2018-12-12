package br.ufma.lsdi.model.enuns;

public enum IndexEnum {

    LOW1(1L),
    LOW2(2L),
    LOW3(3L),
    MODERATE1(4L),
    MODERATE2(5L),
    MODERATE3(6L),
    HIGH1(7L),
    HIGH2(8L),
    HIGH3(9L),
    VERY_HIGH(10L);
    private Long value;

    IndexEnum(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
