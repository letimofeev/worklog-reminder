import React from 'react';
import {Link} from "react-router-dom";

const NotFound = () => {
    return (
        <div className="flex flex-col justify-center items-center h-screen bg-gray-100">
            <h1 className="text-4xl font-bold text-gray-700 mb-4">404 Not Found</h1>
            <p className="text-lg text-gray-700 mb-8">
                We're sorry, the page you're looking for cannot be found.
            </p>
            <Link
                to="/"
                className="px-4 py-2 bg-indigo-600 text-white rounded hover:bg-indigo-700 transition duration-300"
            >
                Go back to homepage
            </Link>
        </div>
    );
};

export default NotFound;
