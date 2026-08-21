# Testing Notes

Small checklist for regression testing while the prototype grows.

## Trading

- [x] Buying shares decreases player cash.
- [x] Selling shares decreases the player's position.
- [x] Market price never becomes negative.
- [x] Invalid tickers are rejected.
- [ ] Add insufficient-liquidity test coverage.
- [ ] Add large-order price-impact test coverage.

## Dividends

- [x] Payouts are proportional to ownership.
- [x] Empty shareholder lists are handled safely.
- [x] Distribution is skipped when the treasury cannot fund it.
- [ ] Add rounding tests for many small shareholders.

## Takeovers

- [x] Majority ownership is detected at the configured threshold.
- [x] Ownership below the threshold does not transfer control.
- [ ] Add takeover cooldown tests.
- [ ] Add server restart persistence test.

## Persistence

Manual test flow:

1. Create two companies.
2. Buy shares in both.
3. Trigger several market ticks.
4. Restart the server.
5. Confirm prices, balances and ownership are restored.

## UI / boards

- [ ] Confirm long company names are truncated cleanly.
- [ ] Confirm negative changes use the down-state formatting.
- [ ] Confirm leaderboard ordering updates after a market tick.
- [ ] Add fallback rendering when fewer than five companies exist.
