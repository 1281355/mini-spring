package com.rain.zbs.servece;

import com.rain.zbs.beans.Bean;

/**
 * @author huangyu
 * @version 1.0
 * @date 2019/9/24 20:22
 */
@Bean
public class SalaryService {

    public Integer calSalary(Integer experience){
        return experience * 5000;
    }
}
