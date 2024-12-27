{ pkgs ? import <nixpkgs> { } }:
pkgs.mkShell {
  buildInputs = [
    pkgs.jdk17

    # Normally I am very against including an IDE into a development environment,
    # but seeing how much the Forge community is tied into this sucker, I don't
    # think I really have a choice. Sorry about the 20 minute download to bring
    # up this repo.
    pkgs.jetbrains.idea-community-src
  ];

  # Set environment variables
  shellHook = ''
    if [ -n "$WAYLAND_DISPLAY" ]; then
      export LD_LIBRARY_PATH="''${LD_LIBRARY_PATH}''${LD_LIBRARY_PATH:+:}${pkgs.libglvnd}/lib"
    fi
  '';
}
