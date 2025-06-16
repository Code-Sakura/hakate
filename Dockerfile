# Builder stage
FROM gradle:jdk17-noble AS builder

# Set working directory
WORKDIR /app

# Copy the project files
COPY . .

# Generate Dokka documentation
RUN gradle --no-daemon :hakate:dokkaHtml

# Final stage
FROM nginx:alpine

# Copy Dokka documentation from builder stage to Nginx HTML directory
COPY --from=builder /app/hakate/build/dokka/html/ /usr/share/nginx/html/

# Expose port 80
EXPOSE 80

# Start Nginaaa
CMD ["nginx", "-g", "daemon off;"]
