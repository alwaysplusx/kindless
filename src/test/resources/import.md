static class DataImportConfiguration {

    @Bean
    CommandLineRunner importDatas(MenuRepository menuRepository, UserRepository userRepository) {
        return (String... args) -> {
            getMenus().forEach(menuRepository::save);
            getUsers().forEach(userRepository::save);
        };
    }

    private List<User> getUsers() {
        MapConfigurationPropertySource source = new MapConfigurationPropertySource(loadProperties());
        return new Binder(source).bind("users", Bindable.listOf(User.class)).get();
    }

    private List<Menu> getMenus() {
        MapConfigurationPropertySource source = new MapConfigurationPropertySource(loadProperties());
        return new Binder(source).bind("menus", Bindable.listOf(Menu.class)).get();
    }

    private Properties loadProperties() {
        YamlPropertiesFactoryBean properties = new YamlPropertiesFactoryBean();
        properties.setResources(new ClassPathResource("data.yml"));
        return properties.getObject();
    }

}