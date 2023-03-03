import {CloudAdapter, ConfigurationBotFrameworkAuthentication} from "botbuilder";
import {
    ConfigurationBotFrameworkAuthenticationOptions
} from "botbuilder-core/src/configurationBotFrameworkAuthentication";
import {BotActivityHandler} from "./bot.activity-handler";

export const botProviders = [
    {
        provide: 'BOT_ADAPTER',
        useFactory: async () => {
            const botFrameworkAuthentication = new ConfigurationBotFrameworkAuthentication({
                MicrosoftAppId: process.env.MICROSOFT_APP_ID,
                MicrosoftAppPassword: process.env.MICROSOFT_APP_PASSWORD,
                MicrosoftAppType: process.env.MICROSOFT_APP_TYPE,
            } as ConfigurationBotFrameworkAuthenticationOptions);
            const adapter = new CloudAdapter(botFrameworkAuthentication);

            adapter.onTurnError = async (context, error) => {
                console.error(`\n [onTurnError] unhandled error: ${error}`);

                await context.sendActivity('The bot encountered an error or bug.');
                await context.sendActivity('To continue to run this bot, please fix the bot source code.');
            };
            return adapter;
        },
    },
    {
        provide: 'BOT_ACTIVITY_HANDLER',
        useFactory: async () => {
            return new BotActivityHandler();
        }
    }
];
