import {TurnContext} from "botbuilder";
import {Injectable, Logger} from "@nestjs/common";
import {UserService} from "../users/user.service";
import {CreateUserQueryDto} from "../users/dtos/create-user.query.dto";

const {ActivityHandler} = require("botbuilder");

@Injectable()
export class BotActivityHandler extends ActivityHandler {
    private readonly logger = new Logger(BotActivityHandler.name);

    constructor(private userService: UserService) {
        super();

        this.onMessage(async (context: TurnContext, next: () => Promise<void>) => {
            await context.sendActivity(`You sent '${context.activity.text}'`);
            await next();
        });

        this.onDialog(async (context: TurnContext, next: () => Promise<void>) => {
            if (context.activity.action === 'add') {
                const user = {
                    skypeId: context.activity.from.id,
                    login: context.activity.from.name,
                    conversationReference: TurnContext.getConversationReference(context.activity)
                } as CreateUserQueryDto;

                userService.create(user)
                    .then(created => this.logger.log(`Saved user with id: ${created.id}`))
                    .catch(error => this.logger.error(`${error.name}: ${error.message}\n ${error.stack}`));
            }
            await next();
        });
    }
}
