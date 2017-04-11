package spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by jiangzhiwen on 17/4/12.
 */
public class MyNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("user", new UserBeanDefinitionParser());
    }
}
