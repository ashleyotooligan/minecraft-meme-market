# Configuration

## Market

`market.tick-seconds` controls how often the simulated exchange advances.

`market.base-volatility` defines random movement before signals are added.

`market.max-price-change-per-tick` limits both upward and downward movement.

`market.minimum-price` prevents negative or zero prices.

## Companies

Creation cost and default capitalization are intentionally server-specific. Economy-heavy servers can increase the cost and share count while SMP-style servers can keep both small.

## Dividends

Dividends are calculated from company treasury rather than created from nothing. This keeps payouts tied to in-game company performance.

## Takeovers

A default control threshold of 51% creates a familiar majority-control mechanic. Set this higher for slower political gameplay.
