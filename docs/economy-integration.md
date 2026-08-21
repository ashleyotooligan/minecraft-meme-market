# Economy Integration

The prototype intentionally keeps currency settlement separate from market mechanics. A production server would normally integrate Vault or a server-specific economy service.

Recommended transaction sequence:

1. Validate ticker and share count.
2. Quote transaction value.
3. Reserve or withdraw player funds.
4. Mutate holdings.
5. Persist company state.
6. Emit the trade event.
7. Roll back holdings if settlement fails.

This separation makes it possible to test the market without running a full economy plugin.
