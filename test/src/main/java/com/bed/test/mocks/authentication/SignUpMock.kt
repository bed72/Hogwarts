package com.bed.test.mocks.authentication

const val SIGN_UP_MOCK = """
{
  "access_token": "eyJhbiOiJIUzI1NiIsImtph...",
  "token_type": "bearer",
  "expires_in": 3600,
  "refresh_token": "eyJhbiOiJIUzI1NiIsImtph...",
  "user": {
    "id": "52cbedfa-d8cb-46c4-8b81-a7dbf82419e7",
    "aud": "authenticated",
    "role": "authenticated",
    "email": "bed@email.com",
    "email_confirmed_at": "2023-08-02T16:53:05.429068Z",
    "phone": "",
    "confirmed_at": "2023-08-02T16:53:05.429068Z",
    "last_sign_in_at": "2023-08-04T23:58:00.047446801Z",
    "app_metadata": {
      "provider": "email",
      "providers": [
        "email"
      ]
    },
    "user_metadata": {},
    "identities": [
      {
        "id": "52cbedfa-d8cb-46c4-8b81-a7dbf82419e7",
        "user_id": "52cbedfa-d8cb-46c4-8b81-a7dbf82419e7",
        "identity_data": {
          "email": "bed@email.com",
          "sub": "52cbedfa-d8cb-46c4-8b81-a7dbf82419e7"
        },
        "provider": "email",
        "last_sign_in_at": "2023-08-02T16:53:05.427663Z",
        "created_at": "2023-08-02T16:53:05.4277Z",
        "updated_at": "2023-08-02T16:53:05.4277Z"
      }
    ],
    "created_at": "2023-08-02T16:53:05.422732Z",
    "updated_at": "2023-08-04T23:58:00.050518Z"
  }
}
"""
