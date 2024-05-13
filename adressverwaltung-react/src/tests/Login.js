import React from 'react';
import { render, fireEvent, waitFor } from '@testing-library/react';
import Login from './Login';

test('renders login form', () => {
  const { getByText, getByLabelText } = render(<Login />);
  const usernameInput = getByLabelText(/Username/i);
  const passwordInput = getByLabelText(/Password/i);
  const loginButton = getByText(/Login/i);
  expect(usernameInput).toBeInTheDocument();
  expect(passwordInput).toBeInTheDocument();
  expect(loginButton).toBeInTheDocument();
});

test('submits login form with username and password', async () => {
  const mockFetch = jest.fn();
  global.fetch = mockFetch;
  mockFetch.mockResolvedValueOnce({
    json: async () => ({ accessToken: 'fake_token', username: 'test_user' }),
  });

  const { getByText, getByLabelText } = render(<Login />);
  const usernameInput = getByLabelText(/Username/i);
  const passwordInput = getByLabelText(/Password/i);
  const loginButton = getByText(/Login/i);

  fireEvent.change(usernameInput, { target: { value: 'test_username' } });
  fireEvent.change(passwordInput, { target: { value: 'test_password' } });
  fireEvent.click(loginButton);

  await waitFor(() => {
    expect(mockFetch).toHaveBeenCalledTimes(1);
    expect(mockFetch).toHaveBeenCalledWith('http://localhost:8080/api/auth/signin', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        username: 'test_username',
        password: 'test_password',
      }),
    });
  });
});