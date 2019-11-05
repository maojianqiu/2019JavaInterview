package JUC.demo05;


public enum CountEnum {
    ONE(1,"齐"),TWO(2,"楚"),THREE(3,"燕"),FOUR(4,"赵"),FIVE(5,"魏"),SIX(6,"韩");


    private Integer retCode;
    private String retMessage;

    CountEnum(Integer retCode, String retMessage) {
        this.retCode = retCode;
        this.retMessage = retMessage;
    }

    public Integer getRetCode() {
        return retCode;
    }

    public String getRetMessage() {
        return retMessage;
    }
    
    public static CountEnum forEach_CountEnum(int index)
    {
        CountEnum[] myArray = CountEnum.values();
        for (CountEnum element:myArray)
        {
            if (index == element.getRetCode()) {
                return element;
            }
        }
        return null;
    }
}
