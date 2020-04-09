# SimpleCommandsLegacy

## How to disable commands

Create config.yml at plugin configuration directory, then:
```yaml
disabledCommands:
 - suicide # disables /suicide (requires simplecommands.suicide)
 - pingall # disables /pingall (requires simplecommands.pingall)
 - ping # disables /ping (doesn't require any permissions)
```