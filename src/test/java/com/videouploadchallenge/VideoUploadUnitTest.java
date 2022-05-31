package com.videouploadchallenge;

import com.videouploadchallenge.config.ApiResponse;
import com.videouploadchallenge.entity.VideoEntity;
import com.videouploadchallenge.repository.VideoRepository;
import com.videouploadchallenge.service.VideoService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(MockitoJUnitRunner.class)
public class VideoUploadUnitTest {
    @Mock
    private VideoRepository videoRepository;
    @InjectMocks
    private VideoService videoService;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    public void testFindAllAgreements() {
//        Mockito.when(agreementRepository.findAll()).thenReturn(new ArrayList<>());
//
//        ApiResponse agreements = agreementService.findAllAgreements();
//
//        Assert.assertNotNull(agreements);
//        Assert.assertEquals(ApiResponse.FAILURE_STATUS, agreements.getStatus());
//    }
//
//    @Test
//    public void testFindAgreement() {
//        Mockito.when(agreementRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(new VideoEntity()));
//
//        ApiResponse agreement = agreementService.findAgreement(1L);
//
//        Assert.assertNotNull(agreement);
//        Assert.assertEquals(ApiResponse.SUCCESS_STATUS, agreement.getStatus());
//    }
//
//    @Test
//    public void testDeleteAgreement() {
//        Mockito.doNothing().when(agreementRepository).deleteById(Mockito.any(Long.class));
//        Mockito.when(agreementRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(new VideoEntity()));
//
//        ApiResponse deletedAgreement = agreementService.deleteAgreement(1L);
//
//        Assert.assertNotNull(deletedAgreement);
//        Assert.assertEquals(ApiResponse.SUCCESS_STATUS, deletedAgreement.getStatus());
//        Assert.assertEquals(ApiResponse.DELETION_SUCCESS_MESSAGE,deletedAgreement.getMessage());
//    }
//
//    @Test
//    public void testFindAgreementsByPropertyId() {
//        Mockito.when(agreementRepository.findAgreementsByPropertyId(Mockito.any(Long.class))).thenReturn(new ArrayList<>());
//
//        ApiResponse agreements = agreementService.findAgreementsByPropertyId(1L);
//
//        Assert.assertNotNull(agreements);
//        Assert.assertEquals(ApiResponse.FAILURE_STATUS, agreements.getStatus());
//    }
}
