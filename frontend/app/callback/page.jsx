'use client';

import { useSearchParams } from "next/navigation";
import { useEffect } from 'react';

export default async function Callback() {
  const searchParams = useSearchParams();
  const code = searchParams.get('code');

  const response = await fetch('/api/callback?code=' + code, {
    method: 'POST'
  });

  window.location.href = '/';

  return null;
}
