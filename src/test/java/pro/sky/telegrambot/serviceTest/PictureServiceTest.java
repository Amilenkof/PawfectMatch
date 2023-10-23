package pro.sky.telegrambot.serviceTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.telegrambot.exceptions.ShelterNotFoundException;
import pro.sky.telegrambot.model.Picture;
import pro.sky.telegrambot.model.Shelter;
import pro.sky.telegrambot.repository.PictureRepository;
import pro.sky.telegrambot.repository.ShelterRepository;
import pro.sky.telegrambot.service.PictureService;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PictureServiceTest {
    @Mock
    private PictureRepository pictureRepository;
    @Mock
    private ShelterRepository shelterRepository;
    @InjectMocks
    private PictureService pictureService;


    private byte[] getbytes() throws IOException {
        Path from = Path.of("src/main/resources/defaultReportPicture.jpg");
        try (InputStream inputStream = Files.newInputStream(from);
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            return byteArrayOutputStream.toByteArray();
        }
    }

    @Test
    public void TestAddSchemaNegative() {
        when(shelterRepository.findById(1L)).thenReturn(Optional.ofNullable(null));
        assertThatThrownBy(() -> pictureService.addSchema(any(), 1L)).isInstanceOf(ShelterNotFoundException.class);
    }

    @Test
    public void TestAddSchemaPositive() throws IOException {
        Shelter shelter = new Shelter("description", "address", "timing", "contacts", "safety", "type");
        Picture expected = new Picture(1L, getbytes(), "/src/main/resources/defaultReportPicture.jpg/1.jpg", 56707L, "jpg", shelter);
        when(shelterRepository.findById(anyLong())).thenReturn(Optional.of(shelter));
        when(pictureRepository.save(any(Picture.class))).thenReturn(expected);
        MultipartFile multipartFile = new MockMultipartFile("testFile", "testFile.jpg", "jpg", new FileInputStream("src/main/resources/defaultReportPicture.jpg"));
        Picture actual = pictureService.addSchema(multipartFile, 1L);
        assertThat(Arrays.equals(actual.getData(), expected.getData()) &&
                   actual.getFileSize().equals(expected.getFileSize()) &&
                   actual.getFilePath().equals(expected.getFilePath()) &&
                   actual.getMediaType().equals(expected.getMediaType())).isTrue();
        verify(pictureRepository).save(any(Picture.class));
    }


    @Test
    public void testFindById() {
        Picture picture = new Picture();
        when(pictureRepository.findById(anyLong())).thenReturn(Optional.of(picture));
        Optional<Picture> actual = pictureService.findById(anyLong());
        assertThat(actual.equals(Optional.of(picture))).isTrue();
        verify(pictureRepository).findById(anyLong());
    }


    @Test
    public void testFindByShelter_id() {
        Picture picture = new Picture();
        when(pictureRepository.findSchemaByShelter_Id(anyLong())).thenReturn(Optional.of(picture));
        Optional<Picture> actual = pictureService.findByShelter_id(anyLong());
        assertThat(actual.equals(Optional.of(picture))).isTrue();
        verify(pictureRepository).findSchemaByShelter_Id(anyLong());
    }


}
