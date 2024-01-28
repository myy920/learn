package operation;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StorageService {

    <T> void excelStore(MultipartFile file, Class<T> clazz);

    <T> void listStore(BaseCRUDMapper<T> mapper, List<T> list);

}
