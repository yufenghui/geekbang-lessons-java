package cn.yufenghui.lession.factory;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.spi.ObjectFactory;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Hashtable;

/**
 * @author : yufenghui
 * @date : 2021/3/7 22:18
 * @Description:
 */
public class MyValidatorFactory implements ObjectFactory {

    @Override
    public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment) throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        // cache the factory somewhere
        Validator validator = factory.getValidator();
        return validator;
    }

}
