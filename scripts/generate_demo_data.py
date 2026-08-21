#!/usr/bin/env python3
"""Generate deterministic demo market rows for screenshots and local tests."""
from dataclasses import dataclass
from random import Random

rng = Random(1337)

@dataclass
class Company:
    name: str
    ticker: str
    price: float
    shares: int

companies = [
    Company("Jeansen Inc.", "JEAN", 32.0, 280),
    Company("Block Labs", "BLOCK", 18.4, 500),
    Company("Creeper Capital", "BOOM", 7.25, 1000),
    Company("Diamond Hands", "DMD", 64.1, 140),
]

for c in companies:
    move = rng.uniform(-0.12, 0.16)
    next_price = max(0.01, c.price * (1 + move))
    print(f"{c.ticker:5} ${next_price:7.2f}  market cap ${next_price*c.shares:10.2f}")
