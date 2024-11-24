package cn.msa.msa_museum_server;

import cn.msa.msa_museum_server.controller.FileController;
import cn.msa.msa_museum_server.dto.FileDto;
import cn.msa.msa_museum_server.dto.ResponseDto;
import cn.msa.msa_museum_server.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class FileControllerTest {

	private MockMvc mockMvc;

	@Mock
	private FileService fileService;

	@InjectMocks
	private FileController fileController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this); // 初始化 Mock 对象
		mockMvc = MockMvcBuilders.standaloneSetup(fileController).build();
	}

	@Test
	void testGetFileList() throws Exception {
		// 模拟分页数据
		List<FileDto> files = Arrays.asList(
				new FileDto("file1.txt", 1024L, "2024-11-24T10:00"),
				new FileDto("file2.jpg", 2048L, "2024-11-23T15:30"),
				new FileDto("file3.pdf", 3072L, "2024-11-22T09:20"),
				new FileDto("file4.docx", 4096L, "2024-11-21T08:15"),
				new FileDto("file5.png", 5120L, "2024-11-20T14:45")
		);

		// 使用 PageImpl 创建分页数据
		Page<FileDto> filePage = new PageImpl<>(files, PageRequest.of(0, 10), files.size());

		// Mock Service 层返回的数据，模拟分页查询返回
		when(fileService.getFileList(any(Pageable.class)))
				.thenReturn(new ResponseDto<>(0, "success", filePage));

		// 执行 GET 请求并验证返回结果
		mockMvc.perform(get("/files")
						.param("page", "0")
						.param("size", "10"))
				.andExpect(status().isOk());
	}
}