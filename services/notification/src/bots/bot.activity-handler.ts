const {ActivityHandler} = require("botbuilder");

export class BotActivityHandler extends ActivityHandler {
    constructor() {
        super();

        this.onMessage(async (context, next) => {
            await context.sendActivity(`You sent '${context.activity.text}'`);
            await next();
        });
    }
}
