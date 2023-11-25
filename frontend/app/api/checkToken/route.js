
'use server';

import currentToken from '../../../utils/token';

export async function GET() {
  const isValid = currentToken.isValid();
  return Response.json({ isValid: isValid });
}
