package de.timmi6790.mpstats.api.versions.v1.bedrock.leaderboard_request;

import com.google.re2j.Matcher;
import com.google.re2j.Pattern;
import de.timmi6790.mpstats.api.Config;
import de.timmi6790.mpstats.api.versions.v1.bedrock.player.BedrockPlayerService;
import de.timmi6790.mpstats.api.versions.v1.bedrock.player.repository.models.BedrockPlayer;
import de.timmi6790.mpstats.api.versions.v1.common.leaderboard_request.LeaderboardRequestService;
import de.timmi6790.mpstats.api.versions.v1.common.models.LeaderboardEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BedrockLeaderboardRequestService extends LeaderboardRequestService<BedrockPlayer> {
    private static final Pattern LEADERBOARD_PATTERN = Pattern.compile("^<td>\\d*<\\/td><td>(.{1,33})<\\/td><td> ([\\d,]*)<\\/td>");

    private final BedrockPlayerService playerService;

    @Autowired
    public BedrockLeaderboardRequestService(final Config config, final BedrockPlayerService playerService) {
        super(config.getLeaderboard().getBedrockUrl(), 100);
        this.playerService = playerService;
    }

    @Override
    protected Optional<LeaderboardEntry<BedrockPlayer>> parseRow(final String row) {
        final Matcher leaderboardMatcher = LEADERBOARD_PATTERN.matcher(row);
        if (leaderboardMatcher.find()) {
            final String playerName = leaderboardMatcher.group(1);
            return Optional.of(
                    new LeaderboardEntry<>(
                            this.playerService.getPlayerOrCreate(playerName),
                            Long.parseLong(leaderboardMatcher.group(2).replace(",", ""))
                    )
            );
        }
        return Optional.empty();
    }
}