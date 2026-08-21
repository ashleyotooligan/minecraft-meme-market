# Price Engine

The current price model is designed for entertainment and server gameplay.

For company price `P`, the next tick is:

```text
P_next = max(min_price, P * (1 + clamp(noise + revenue + orders + news)))
```

Signals:

- `noise`: Gaussian random walk scaled by base volatility
- `revenue`: bounded positive/negative signal based on revenue vs market cap
- `orders`: bounded buy/sell pressure from recent player trading
- `news`: temporary sentiment modifier that decays each market tick

A per-tick clamp prevents one event from moving a quote by more than the configured maximum.
