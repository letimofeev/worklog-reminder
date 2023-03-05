import {ActivityHandler, CloudAdapter, ConfigurationBotFrameworkAuthentication, TurnContext} from "botbuilder";
import {
    ConfigurationBotFrameworkAuthenticationOptions
} from "botbuilder-core/src/configurationBotFrameworkAuthentication";
import {BotActivityHandler} from "./bot.activity-handler";
import {Logger} from "@nestjs/common";

export const botProviders = [
    {
        provide: CloudAdapter,
        useFactory: async () => {
            const botFrameworkAuthentication = new ConfigurationBotFrameworkAuthentication({
                MicrosoftAppId: process.env.MICROSOFT_APP_ID,
                MicrosoftAppPassword: process.env.MICROSOFT_APP_PASSWORD,
                MicrosoftAppType: process.env.MICROSOFT_APP_TYPE,
            } as ConfigurationBotFrameworkAuthenticationOptions);

            const adapter = new CloudAdapter(botFrameworkAuthentication);
            const logger = new Logger(CloudAdapter.name);

            adapter.onTurnError = async (context: TurnContext, error: Error) => {
                logger.error(`Unhandled error ${error.name}: ${error.stack}`);
                await context.sendActivity('The bot encountered an error or bug.');
            };

            return adapter
        },
    },
    {
        provide: ActivityHandler,
        useClass: BotActivityHandler
    }
];
