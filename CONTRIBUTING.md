# Contributing

Thanks for helping improve Minecraft Meme Market.

## Development flow

1. Fork the repository.
2. Create a small, focused branch.
3. Keep market rules inside service classes rather than command handlers.
4. Add tests for deterministic mechanics such as dividends and takeover thresholds.
5. Run `mvn test` before opening a pull request.

## Style

Prefer small classes, explicit names and readable game logic over clever abstractions. User-facing text should stay short enough for Minecraft chat and signs.
