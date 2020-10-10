package com.org.moer.TV;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
//@NoArgsConstructor
//@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class XiaoMiTV extends TV {
    private long price;
    private String color;
    public XiaoMiTV(String id,String name,long price,String color){
        super(id,name);
        this.price = price;
        this.color = color;
    }
}
