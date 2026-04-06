package hangeureut.global.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 비동기 처리 스레드 풀 설정
 */
@Configuration
@EnableAsync
public class AsyncConfig {

	@Bean(name = "visionThreadPool")
	public ExecutorService visionThreadPool() {
		return createVisionThreadPool();
	}

	public static ExecutorService createVisionThreadPool() {
		int corePoolSize = 3;
		int maxPoolSize = 5;
		int queueCapacity = 100;

		ThreadPoolExecutor executor = new ThreadPoolExecutor(
			corePoolSize,
			maxPoolSize,
			60L,
			TimeUnit.SECONDS,
			new LinkedBlockingQueue<>(queueCapacity),
			new ThreadPoolExecutor.CallerRunsPolicy()
		);

		return executor;
	}
}
