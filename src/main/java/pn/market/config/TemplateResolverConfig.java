package pn.market.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class TemplateResolverConfig {

//    @Bean
//    public ViewResolver viewResolver(SpringTemplateEngine templateEngine) {
//        ThymeleafReactiveViewResolver resolver = new ThymeleafReactiveViewResolver();
//        resolver.setTemplateEngine((ISpringWebFluxTemplateEngine) templateEngine);
//        return resolver;
//    }
//    @Bean
//    public ViewResolver  resolver(ISpringWebFluxTemplateEngine templateEngine) {
//        ThymeleafReactiveViewResolver resolver = new ThymeleafReactiveViewResolver();
//        resolver.setTemplateEngine( templateEngine);
//        return resolver;
//    }


    //--------------------------

    /*
    //...
    @Bean
    public ITemplateResolver thymeleafTemplateResolver() {
        final SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
     //   resolver.setApplicationContext(this.context);
        resolver.setPrefix("classpath:views/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCacheable(false);
        resolver.setCheckExistence(false);
        return resolver;
    }
    @Bean
    public ISpringWebFluxTemplateEngine thymeleafTemplateEngine() {
        SpringWebFluxTemplateEngine templateEngine = new SpringWebFluxTemplateEngine();
        templateEngine.setTemplateResolver(thymeleafTemplateResolver());
        return templateEngine;
    }
    @Bean
    public ThymeleafReactiveViewResolver thymeleafReactiveViewResolver() {
        ThymeleafReactiveViewResolver viewResolver = new ThymeleafReactiveViewResolver();
        viewResolver.setTemplateEngine(thymeleafTemplateEngine());
        return viewResolver;
    }
//    @Override
//    public void configureViewResolvers(ViewResolverRegistry registry) {
//        registry.viewResolver(thymeleafReactiveViewResolver());
//    }
    //...


    //********************************************************

    @Bean
    public ThymeleafReactiveViewResolver thymeleafReactiveViewResolver() {
        ThymeleafReactiveViewResolver viewResolver = new ThymeleafReactiveViewResolver();
        viewResolver.setTemplateEngine(thymeleafTemplateEngine());
        return viewResolver;
    }

    @Bean
    public ISpringWebFluxTemplateEngine thymeleafTemplateEngine() {
        SpringWebFluxTemplateEngine templateEngine = new SpringWebFluxTemplateEngine();
        templateEngine.setTemplateResolver(thymeleafTemplateResolver());
        return templateEngine;
    }

    @Bean
    public ThymeleafReactiveViewResolver thymeleafReactiveViewResolver() {
        ThymeleafReactiveViewResolver viewResolver = new ThymeleafReactiveViewResolver();
        viewResolver.setTemplateEngine(thymeleafTemplateEngine());
        return viewResolver;
    }
//    @Override
//    public void configureViewResolvers(ViewResolverRegistry registry) {
//        registry.viewResolver(thymeleafReactiveViewResolver());
    }


     */
}
