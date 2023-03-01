const {adapter, bot} = require("../bot");

class BotController {
    async sendMessage(request, response) {
        await adapter.process(request, response, (context) => bot.run(context));
    }
}

module.exports = new BotController()
