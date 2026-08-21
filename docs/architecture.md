# Architecture

Minecraft Meme Market is split into small services so market mechanics can evolve independently.

## Runtime layers

### Commands and listeners
Translate Minecraft interactions into calls against `MarketService`.

### MarketService
The main application service. Owns listed companies, routes trades, schedules price updates and coordinates persistence.

### PriceEngine
Calculates the next price from volatility, order pressure, revenue and news impact.

### NewsService
Applies temporary positive or negative market sentiment and decays it over time.

### DividendService
Calculates proportional shareholder distributions from company treasury balances.

### TakeoverService
Checks whether one shareholder owns enough of the total share count to control a company.

### Persistence
`MarketRepository` is the abstraction. The prototype ships with `YamlMarketRepository`; a SQL backend can be added without changing gameplay code.

## Future boundaries

Planned adapters include Vault for player balances, MiniMessage for text components, a SQL repository, inventory GUIs and a small HTTP read-only market API.
