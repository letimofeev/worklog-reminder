import React from "react";
import {NotificationUser} from "../../../models/notification/NotificationUser";
import {NotificationUserFormErrors} from "../../../validation/NotificationUserFormErrors";
import {NotificationUserFormValidator} from "../../../validation/NotificationUserFormValidator";

type EditNotificationUserContentProps = {
    formData: NotificationUser;
    setFormData: (formData: NotificationUser) => void;
    formErrors: NotificationUserFormErrors;
    setFormErrors: (formErrors: NotificationUserFormErrors) => void;
    onUpdate: (updatedUser: NotificationUser) => void;
    onClose: () => void;
}

const EditNotificationUserContent: React.FC<EditNotificationUserContentProps> = (
    {
        formData,
        setFormData,
        formErrors,
        setFormErrors,
        onUpdate,
        onClose,
    }) => {
    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        setFormData({...formData, [e.target.name]: e.target.value});
    };

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        const trimmedFormData = trimUserData(formData);
        setFormData(trimmedFormData)
        if (validateForm(trimmedFormData)) {
            onUpdate(formData);
        }
    };

    const trimUserData = (user: NotificationUser): NotificationUser => {
        return {
            ...user,
            displayName: user.displayName.trim(),
            login: user.login.trim()
        };
    };

    const validateForm = (formData: NotificationUser) => {
        let [isValid, errors] = NotificationUserFormValidator.validateForm(formData);
        setFormErrors(errors);
        return isValid;
    };

    return (
        <form onSubmit={handleSubmit} className="bg-white rounded px-8 pt-6 pb-8 w-[700px] h-[500px]">
            <h2 className="text-xl font-bold mb-4">Edit Employee</h2>
            <p className="mb-6 text-gray-600">Update the employee information as needed</p>
            <div className="space-y-6">
                <div className="grid grid-cols-2 gap-4 mb-4">
                    <div>
                        <label htmlFor="login" className="block text-gray-700 text-sm font-bold mb-2">
                            Skype login
                        </label>
                        <input
                            type="text"
                            name="login"
                            id="login"
                            value={formData.login}
                            onChange={handleChange}
                            className={`appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:border-indigo-500" ${
                                formErrors.login ? 'border-red-500' : 'border-gray-200'
                            }`}
                        />
                        {formErrors.login &&
                            <div className="absolute mt-1 text-xs text-red-500">
                                {formErrors.login}
                            </div>
                        }
                    </div>
                    <div>
                        <label htmlFor="displayName" className="block text-gray-700 text-sm font-bold mb-2">
                            Display Name
                        </label>
                        <input
                            type="text"
                            name="displayName"
                            id="displayName"
                            value={formData.displayName !== null ? formData.displayName : ''}
                            onChange={handleChange}
                            className={`appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:border-indigo-500" ${
                                formErrors.displayName ? 'border-red-500' : 'border-gray-200'
                            }`}
                        />
                        {formErrors.displayName &&
                            <div className="absolute mt-1 text-xs text-red-500">
                                {formErrors.displayName}
                            </div>
                        }
                    </div>
                </div>
                <div className="items-center mb-4">
                    <label htmlFor="notificationEnabled" className="text-gray-700 text-sm font-bold mr-8">
                        Notification Enabled
                    </label>
                    <input
                        type="checkbox"
                        name="notificationEnabled"
                        id="notificationEnabled"
                        checked={formData.enabled}
                        onChange={(e) => setFormData({...formData, enabled: e.target.checked})}
                        className="focus:outline-none focus:ring-2 focus:ring-indigo-500"
                    />
                </div>
            </div>
            <div className="flex items-center justify-end mt-6 space-x-4">
                <button
                    type="submit"
                    className="bg-indigo-600 hover:bg-indigo-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:border-indigo-700"
                >
                    Save
                </button>
                <button
                    type="button"
                    onClick={onClose}
                    className="bg-white hover:bg-gray-100 text-gray-700 font-bold py-2 px-4 rounded border border-gray-300 focus:outline-none focus:border-indigo-500"
                >
                    Cancel
                </button>
            </div>
        </form>
    );
};

export default EditNotificationUserContent;
