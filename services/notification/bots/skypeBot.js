const {ActivityHandler} = require("botbuilder");

class SkypeBot extends ActivityHandler {
    constructor() {
        super();

        this.onMessage(async (context, next) => {
            await context.sendActivity(`You sent '${context.activity.text}'`);
            await next();
        });
    }
}

module.exports = SkypeBot
