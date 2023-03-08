import React from 'react';
import './empNotificationStatus.scss';

interface EmpNotificationStatusProps {
    notificationEnabled: boolean;
    botConnected: boolean;
}

const EmpNotificationStatus: React.FC<EmpNotificationStatusProps> = ({notificationEnabled, botConnected}) => {
    return (
        <div className="emp-notification-status">
            {(botConnected && notificationEnabled) &&
                <div className="emp-notification-status__connected">
                    Connected
                </div>
            }
            {(botConnected && !notificationEnabled) &&
                <div className="emp-notification-status__disabled">
                    Disabled
                </div>
            }
            {(!botConnected && !notificationEnabled) &&
                <div className="emp-notification-status__disconnected">
                    Disconnected
                </div>
            }
        </div>
    );
};

export default EmpNotificationStatus;