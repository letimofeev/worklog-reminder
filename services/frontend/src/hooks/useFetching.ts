import {useState} from "react";

type CallbackFunctionType = (...args: any[]) => any;

export const useFetching = (callback: CallbackFunctionType): [CallbackFunctionType, boolean, any] => {
    const [isLoading, setIsLoading] = useState(false)
    const [error, setError] = useState('')

    const fetching = async (...args: any[]) => {
        try {
            setIsLoading(true)
            await callback(...args)
        } catch (e) {
            let message = 'Unknown Error'
            if (e instanceof Error) {
                message = e.message
            }
            setError(message)
        } finally {
            setIsLoading(false)
        }
    }

    return [fetching, isLoading, error]
}
