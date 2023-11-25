'use client';

import { useSearchParams } from "next/navigation";
import { useEffect } from 'react';

export default function Callback() {
    const searchParams = useSearchParams();
    const code = searchParams.get('code');
    
    useEffect(() => {
        const callback = async () => {
            const response = await fetch('/api/callback?code=' + code, {
                method: 'POST'
            }); 

            if (response.status == 200) {
                window.location.href = '/dashboard';
            } else {
                // TODO Show toast notification with error message
                window.location.href = '/';
            }
        };

        callback();
    });

    return null;
}