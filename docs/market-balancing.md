# Market Balancing Notes

This document tracks tuning decisions for the Minecraft Meme Market price engine.

## Goals

- Keep early-game companies affordable.
- Prevent a single trade from moving a liquid company too far.
- Allow low-float meme companies to remain volatile.
- Make company revenue matter without overpowering player activity.
- Keep news events noticeable but temporary.

## Current tuning targets

| Setting | Target | Notes |
|---|---:|---|
| Base volatility | `0.035` | Default background movement per market tick |
| Max move per tick | `20%` | Hard clamp against extreme jumps |
| Takeover threshold | `51%` | Majority ownership required for control |
| Dividend minimum | `$1.00` | Avoids tiny payout spam |
| Market tick | `30s` | Fast enough to feel live without excessive updates |

## Price impact

The price engine combines trading pressure with a liquidity factor. High-float companies should require more volume to move, while smaller meme companies can react much faster.

Pseudo model:

```text
priceMove =
    buySellPressure
  + revenueMomentum
  + activeNewsImpact
  + randomVolatility

priceMove *= liquidityModifier
priceMove = clamp(priceMove, -maxTickMove, +maxTickMove)
```

## Next balancing pass

- Compare price movement for 100, 1,000 and 10,000-share orders.
- Add separate volatility profiles for stable and meme companies.
- Reduce repeated news stacking on the same ticker.
- Add a minimum liquidity floor for newly-created companies.
- Test dividend payouts after large treasury changes.

These values are intentionally game-oriented rather than simulations of a real-world exchange.
