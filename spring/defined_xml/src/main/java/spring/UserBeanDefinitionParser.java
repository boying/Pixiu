package spring;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * Created by jiangzhiwen on 17/4/11.
 */
public class UserBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
    @Override
    protected Class<?> getBeanClass(Element element) {
        return User.class;
    }

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder builder) {
        String userName = element.getAttribute("userName");
        String email = element.getAttribute("email");

        // 将提取的数据放入到 BeanDefinitionBuilder 中，待到完成所有 bean 的解析后统一注册到 beanFactory 中
        if (StringUtils.hasText(userName)) {
            builder.addPropertyValue("userName", userName);

        }

        if (StringUtils.hasText(email))
            builder.addPropertyValue("email", email);
    }
}
