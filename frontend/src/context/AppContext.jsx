import { createContext, useEffect, useState } from "react";
import { AppConstants } from "../util/constants.js";
import axios from "axios";
import { toast } from "react-toastify";

export const AppContext = createContext();

export const AppProvider = (props) => {
    axios.defaults.withCredentials = true;
    const backendUrl = AppConstants.BACKEND_URL;

    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [userData, setUserData] = useState(null);
    const [loading, setLoading] = useState(true); // 🔄 Added loading state

    const getUserData = async () => {
        try {
            const response = await axios.get(backendUrl + "/profile");
            if (response.status === 200) {
                setUserData(response.data);
            } else {
                toast.error("Unable to fetch user data");
            }
        } catch (err) {
            toast.error(err.message);
        }
    };

    const getAuthState = async () => {
        setLoading(true); // Start loading
        try {
            const response = await axios.get(backendUrl + "/is-authenticated");
            if (response.status === 200 && response.data === true) {
                setIsLoggedIn(true);
                await getUserData();
            } else {
                setIsLoggedIn(false);
                setUserData(null);
            }
        } catch (err) {
            console.log(err);
        } finally {
            setLoading(false); // Done loading
        }
    };

    useEffect(() => {
        getAuthState();
    }, []);

    const contextValue = {
        backendUrl,
        isLoggedIn,
        setIsLoggedIn,
        userData,
        setUserData,
        getUserData,
        loading, // 🔄 Exposed for pages to use
    };

    return (
        <AppContext.Provider value={contextValue}>
            {props.children}
        </AppContext.Provider>
    );
};
