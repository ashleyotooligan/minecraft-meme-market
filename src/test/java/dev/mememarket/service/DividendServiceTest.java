package dev.mememarket.service;

import dev.mememarket.model.Company;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DividendServiceTest {
    @Test
    void splitsDividendByOwnership() {
        UUID a = UUID.randomUUID();
        UUID b = UUID.randomUUID();
        Company c = new Company("Block Labs", "BLOCK", a, 10.0, 100);
        c.treasury(1_000.0);
        c.holdings().put(a, 75);
        c.holdings().put(b, 25);

        var payouts = new DividendService().calculatePayouts(c, 0.10);
        assertEquals(75.0, payouts.get(a), 0.0001);
        assertEquals(25.0, payouts.get(b), 0.0001);
    }
}
