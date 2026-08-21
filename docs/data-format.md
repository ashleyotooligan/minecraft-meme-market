# Data Format

Company data is stored under `plugins/MinecraftMemeMarket/companies.yml`.

```yaml
companies:
  BLOCK:
    name: Block Labs
    ceo: 00000000-0000-0000-0000-000000000000
    price: 18.4
    total-shares: 100
    treasury: 4200
    revenue-today: 360
    holdings:
      00000000-0000-0000-0000-000000000000: 60
```

The YAML repository is intended for prototypes and small servers. Production deployments should eventually use a transactional database backend.
