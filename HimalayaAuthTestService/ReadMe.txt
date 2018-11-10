How to integrate spring boot with spring security
	dependency
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-test</artifactId>
			<scope>test</scope>
		</dependency>
		
	1. Hard code Role int config class
		@Configuration
		@EnableWebSecurity
		public class AuthSecurityConfig extends WebSecurityConfigurerAdapter {
			@Override
			protected void configure(AuthenticationManagerBuilder auth) throws Exception {
				auth.inMemoryAuthentication().withUser("admin").password("123456").roles("ADMIN","USER");
				auth.inMemoryAuthentication().withUser("demo").password("demo").roles("USER");
			}
			
			@Override
			public void configure(WebSecurity web) throws Exception {
				web.ignoring().antMatchers("/js/**", "/css/**", "/images/**");
			}
		
		    @Override
		    protected void configure(HttpSecurity http) throws Exception {
		        http.authorizeRequests()
		                .antMatchers("/").permitAll()
		                .antMatchers("/admin").hasRole("ADMIN")
		                .antMatchers("/index").hasRole("USER")
		                .antMatchers("/user").hasRole("USER")
		                .anyRequest().authenticated()
		                .and()
		                .logout().permitAll()
		                .and()
		                .formLogin();
		        http.csrf().disable();
		    }
		}  
		
	2. 	Controller
	@RestController
	public class BaseController {
	
		private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);
		
		@RequestMapping("/")
		public String index(){
			LOGGER.debug("Auth server is running...");
			return "Auth server is running...";
		}
		
		@PreAuthorize("hasRole('ROLE_ADMIN')")
		@RequestMapping("/admin")
		public String admin(){
			LOGGER.debug("Auth Admin");
			return "Auth Admin";
		}
		
		@PreAuthorize("hasRole('ROLE_USER')")
		@RequestMapping("/user")
		public String user(){
			LOGGER.debug("Auth User");
			return "Auth User";
		}
	}
	
	3.	Run App		
		@EnableAutoConfiguration
		@EnableGlobalMethodSecurity(prePostEnabled=true)
		@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
		public class HimalayaAuthServiceApplication {
		
			public static void main(String[] args) {
				SpringApplication.run(HimalayaAuthServiceApplication.class, args);
			}
		}
	
	4. 	Testing
		@RunWith(SpringRunner.class)
		@SpringBootTest
		@AutoConfigureMockMvc
		public class BaseControllerTest {
		
			@Autowired
			MockMvc mockMvc;
			
			@Test
			public void testIndex() throws Exception{
				mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(status().isOk());
			}
			
			@Test
			public void testAdmin() throws Exception{
				mockMvc.perform(MockMvcRequestBuilders.get("/admin")).andExpect(status().isOk());
			}
			
			@Test
			@WithMockUser(username="admin", password="123456", roles={"ADMIN","USER"})
			public void testAuth() throws Exception{
				mockMvc.perform(MockMvcRequestBuilders.get("/admin")).andExpect(status().isOk());
			}
			
			@Test
			@WithMockUser(username="demo", password="demo", roles={"USER"})
			public void testUser() throws Exception{
				mockMvc.perform(MockMvcRequestBuilders.get("/user")).andExpect(status().isOk());
			}
		}
	
InMemory UserDetails Manager Authentication
	1. comment below line from run app
	//@EnableGlobalMethodSecurity(prePostEnabled=true)
	
	2. remove below line from Controller
	//	@PreAuthorize("hasRole('ROLE_ADMIN')")
	
	3. change SecutiryConfig as below
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/index").hasRole("USER")
                .antMatchers("/user").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .logout().permitAll()
                .and()
                .formLogin();
        http.csrf().disable();
    }
	 
	 /**
	 * 用户信息服务
	 */
	@Bean
	public UserDetailsService userDetailsService() {
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager(); // 在内存中存放用户信息
		manager.createUser(User.withUsername("admin").password("123456").roles("USER","ADMIN").build());
		manager.createUser(User.withUsername("demo").password("demo").roles("USER").build());
		return manager;
	}
	
	/**
	 * 认证信息管理
	 * @param auth
	 * @throws Exception
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService());
	}
	
	
JDBC Authentication:
	Create a new class to derive UserDetailsService
	implement the fetching logic in getUserDetailsByUsername();
	
	
	
	
	
Integrating Springboot with Mybatis, using Mybatis Auto Generator to generate the source code
	
	add below dependencies:
	<dependency>
		<groupId>mysql</groupId>
		<artifactId>mysql-connector-java</artifactId>				
		<version>${mybatis.generator.version}</version>
		<scope>runtime</scope>
	</dependency>
	<!-- https://mvnrepository.com/artifact/org.mybatis.generator/mybatis-generator-core -->
	<dependency>
		<groupId>org.mybatis.generator</groupId>
		<artifactId>mybatis-generator-core</artifactId>
		<version>${mybatis.generator.version}</version>
	</dependency>	
	
	
	add below plugin:
	<plugin>
		<groupId>org.mybatis.generator</groupId>
		<artifactId>mybatis-generator-maven-plugin</artifactId>
		<configuration>
			<verbose>true</verbose>
			<overwrite>true</overwrite>
			<configurationFile>src/main/resources/generatorConfig.xml</configurationFile>
		</configuration>
		<dependencies>
			<!-- 数据库驱动 -->
			<dependency>
				<groupId>mysql</groupId>
				<artifactId>mysql-connector-java</artifactId>
				<version>5.1.40</version>
			</dependency>
		</dependencies>
	</plugin>
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	    