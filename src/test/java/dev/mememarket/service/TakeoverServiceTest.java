package dev.mememarket.service;

import dev.mememarket.model.Company;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TakeoverServiceTest {
    @Test
    void detectsMajorityOwner() {
        UUID founder = UUID.randomUUID();
        UUID buyer = UUID.randomUUID();
        Company c = new Company("Creeper Capital", "BOOM", founder, 8.0, 100);
        c.holdings().put(buyer, 51);
        assertEquals(buyer, new TakeoverService(0.51).controllingShareholder(c).orElseThrow());
    }

    @Test
    void ignoresMinorityOwner() {
        UUID founder = UUID.randomUUID();
        Company c = new Company("Creeper Capital", "BOOM", founder, 8.0, 100);
        c.holdings().put(UUID.randomUUID(), 49);
        assertTrue(new TakeoverService(0.51).controllingShareholder(c).isEmpty());
    }
}
