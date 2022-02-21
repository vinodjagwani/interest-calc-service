package au.theprogrampractice.interest.feature.interest.service.impl;

import au.theprogrampractice.interest.feature.interest.repository.BalanceInterestRepository;
import au.theprogrampractice.interest.feature.interest.repository.entity.BalanceInterest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class BalanceInterestServiceImplTest {

    @Mock
    BalanceInterestRepository balanceInterestRepository;

    @InjectMocks
    BalanceInterestServiceImpl balanceInterestService;

    @Test
    void testFindByBalanceDateBetween() {
        when(balanceInterestRepository.findByBalanceDateBetween(any(LocalDate.class), any(LocalDate.class))).thenReturn(Flux.just(new BalanceInterest()));
        balanceInterestService.findByBalanceDateBetween(LocalDate.MIN, LocalDate.MAX);
        verify(balanceInterestRepository, atLeastOnce()).findByBalanceDateBetween(any(LocalDate.class), any(LocalDate.class));
    }

    @Test
    void testSaveAll() {
        when(balanceInterestRepository.saveAll(any(Publisher.class))).thenReturn(Flux.just(new BalanceInterest()));
        balanceInterestService.saveAll(Flux.just(new BalanceInterest()));
        verify(balanceInterestRepository, atLeastOnce()).saveAll(any(Publisher.class));
    }

    @Test
    void testDeleteByBsbAndIdentificationAndBalanceDate() {
        when(balanceInterestRepository.deleteByBsbAndIdentificationAndBalanceDate(any(String.class), any(String.class), any(LocalDate.class))).thenReturn(Mono.empty());
        balanceInterestService.deleteByBsbAndIdentificationAndBalanceDate("3242", "2342", LocalDate.now());
        verify(balanceInterestRepository, atLeastOnce()).deleteByBsbAndIdentificationAndBalanceDate(any(String.class), any(String.class), any(LocalDate.class));
    }
}
