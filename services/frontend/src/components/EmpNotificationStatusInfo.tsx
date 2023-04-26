import React from 'react';
import EmpNotificationStatus from "./EmpNotificationStatus";
import '../styles/empNotificationStatusInfo.scss'

const EmpNotificationStatusInfo = () => {
    return (
        <div className="emp-notification-status-info">
            <div className="emp-notification-status-info__title">
                Employee Notification Status
            </div>
            <div className="emp-notification-status-info__header">
                <div className="emp-notification-status-info__status__header-cell">
                    Status
                </div>
                <div className="emp-notification-status-info__description__header-cell">
                    Description
                </div>
            </div>
            <div className="emp-notification-status-info__body">
                <div className="emp-notification-status-info__body-row">
                    <div className="emp-notification-status-info__status__body-cell">
                        <EmpNotificationStatus
                            notificationEnabled={true}
                            botConnected={true}
                        />
                    </div>
                    <div className="emp-notification-status-info__description__body-cell">
                        Bot connected, notifications active
                    </div>
                </div>
                <div className="emp-notification-status-info__body-row">
                    <div className="emp-notification-status-info__status__body-cell">
                        <EmpNotificationStatus
                            notificationEnabled={false}
                            botConnected={true}
                        />
                    </div>
                    <div className="emp-notification-status-info__description__body-cell">
                        Bot connected, but notifications disabled for employee
                    </div>
                </div>
                <div className="emp-notification-status-info__body-row">
                    <div className="emp-notification-status-info__status__body-cell">
                        <EmpNotificationStatus
                            notificationEnabled={false}
                            botConnected={false}
                        />
                    </div>
                    <div className="emp-notification-status-info__description__body-cell">
                        Bot disconnected, notifications inactive
                    </div>
                </div>
            </div>
        </div>
    );
};

export default EmpNotificationStatusInfo;
