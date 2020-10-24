import { render, screen } from '@testing-library/react';
import App from './App';

test('renders application title', () => {
  render(<App />);

  const linkElement = screen.getByText(/GeoAdmin/i);

  expect(linkElement).toBeInTheDocument();
});
